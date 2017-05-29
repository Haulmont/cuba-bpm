package com.company.workshop.web.order;

import com.company.workshop.entity.Order;
import com.company.workshop.entity.OrderStatus;
import com.company.workshop.service.OrderService;
import com.haulmont.bpm.entity.ProcActor;
import com.haulmont.bpm.entity.ProcDefinition;
import com.haulmont.bpm.entity.ProcInstance;
import com.haulmont.bpm.gui.procactions.ProcActionsFrame;
import com.haulmont.bpm.service.ProcessRuntimeService;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.GroupBoxLayout;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.components.ValidationErrors;
import com.haulmont.cuba.security.global.UserSession;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class OrderEdit extends AbstractEditor<Order> {

    private static String PROCESS_CODE = "orderProcessing";

    @Inject
    private OrderService orderService;

    @Inject
    private ProcActionsFrame procActionsFrame;

    @Named("fieldGroup.hoursSpent")
    private TextField hoursSpentField;

    @Inject
    private GroupBoxLayout procActionsBox;
    @Inject
    private ProcessRuntimeService processRuntimeService;
    @Inject
    private DataManager dataManager;
    @Inject
    private Metadata metadata;
    @Inject
    private UserSession userSession;

    @Override
    protected void initNewItem(Order item) {
        super.initNewItem(item);

        item.setStatus(OrderStatus.NEW);
    }

    @Override
    protected boolean preCommit() {
        Order order = getItem();
        order.setAmount(orderService.calculateAmount(order));

        return super.preCommit();
    }

    public void refreshAmount() {
        Order order = getItem();
        order.setAmount(orderService.calculateAmount(order));
    }

    @Override
    protected void postValidate(ValidationErrors errors) {
        if (getItem().getHoursSpent() != null && getItem().getHoursSpent() < 0) {
            errors.add(hoursSpentField, "'Hours Spent' must be greater or equal to 0");
        }
    }

    @Override
    protected void postInit() {
        super.postInit();

        initProcActionsFrame();

        if (!PersistenceHelper.isNew(getItem())) {
            procActionsBox.setVisible(true);
        }
    }

    private void initProcActionsFrame() {
        if (!PersistenceHelper.isNew(getItem())) {
            procActionsFrame.initializer()
                    .setBeforeStartProcessPredicate(this::commit)
                    .setAfterStartProcessListener(() -> {
                        showNotification("Process started");

                        close(CLOSE_ACTION_ID);
                    })
                    .setBeforeCompleteTaskPredicate(this::commit)
                    .setAfterCompleteTaskListener(() -> {
                        showNotification("Task completed");

                        close(COMMIT_ACTION_ID);
                    })
                    .setCancelProcessEnabled(false)
                    .init(PROCESS_CODE, getItem());
        }
    }

    public void startProcess() {
        commit();

        ProcDefinition procDefinition = dataManager.load(
                LoadContext.create(ProcDefinition.class)
                        .setQuery(new LoadContext.Query("select pd from bpm$ProcDefinition pd where pd.code = :code")
                                .setParameter("code", PROCESS_CODE))
                        .setView("procDefinition-procInstanceEdit")
        );

        ProcInstance procInstance = metadata.create(ProcInstance.class);
        procInstance.setProcDefinition(procDefinition);
        procInstance.setEntityId(getItem().getUuid());
        procInstance.setEntityName(getItem().getMetaClass().getName());

        Set<ProcActor> procActors = new HashSet<>();

        ProcActor manager = metadata.create(ProcActor.class);
        manager.setUser(userSession.getUser());
        manager.setOrder(0);
        manager.setProcInstance(procInstance);
        manager.setProcRole(procDefinition.getProcRoles().get(0));

        ProcActor mechanic = metadata.create(ProcActor.class);
        mechanic.setUser(getItem().getMechanic().getUser());
        mechanic.setOrder(1);
        mechanic.setProcInstance(procInstance);
        mechanic.setProcRole(procDefinition.getProcRoles().get(1));

        procActors.add(manager);
        procActors.add(mechanic);

        procInstance.setProcActors(procActors);

        Set<Entity> committed = dataManager.commit(new CommitContext()
                .addInstanceToCommit(manager)
                .addInstanceToCommit(mechanic)
                .addInstanceToCommit(procInstance));

        ProcInstance committedProcInstance = (ProcInstance) committed.stream()
                .filter(p -> p.equals(procInstance))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Error"));

        processRuntimeService.startProcess(committedProcInstance, "Started!", new HashMap<>());

        close(COMMIT_ACTION_ID);
    }
}