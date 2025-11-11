
document.addEventListener('DOMContentLoaded', (event) => {


    const tbody = document.getElementById('task-list-tbody');


    if (!tbody) {
        return;
    }

    const editBtn = document.getElementById('edit-order-btn');
    const saveBtn = document.getElementById('save-order-btn');
    const taskForm = document.getElementById('form-task-order');

    const sortable = Sortable.create(tbody, {
        animation: 150,
        disabled: true,

    });


    editBtn.addEventListener('click', () => {
        sortable.option('disabled', false);
        editBtn.style.display = 'none';
        saveBtn.style.display = 'inline-block';


        tbody.classList.add('editing-order');
    });


    taskForm.addEventListener('submit', (event) => {


        event.preventDefault();

        const rows = tbody.querySelectorAll('tr[data-task-id]');


        rows.forEach(row => {

            const taskId = row.dataset.taskId;


            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = 'taskOrder';
            input.value = taskId;

            taskForm.appendChild(input);
        });

        sortable.option('disabled', true);
        editBtn.style.display = 'inline-block';
        saveBtn.style.display = 'none';
        tbody.classList.remove('editing-order');

        taskForm.submit();
    });
});