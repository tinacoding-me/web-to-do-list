// Nội dung file static/js/dragdrop.js

// Chờ cho toàn bộ trang web được tải xong
document.addEventListener('DOMContentLoaded', (event) => {

    // 1. Tìm các đối tượng trên trang
    const tbody = document.getElementById('task-list-tbody');

    // Rất quan trọng: Nếu không tìm thấy tbody (ví dụ: ở trang /projects),
    // thì không làm gì cả để tránh lỗi.
    if (!tbody) {
        return;
    }

    const editBtn = document.getElementById('edit-order-btn');
    const saveBtn = document.getElementById('save-order-btn');
    const taskForm = document.getElementById('form-task-order');

    // 2. Khởi tạo thư viện SortableJS
    // Nó sẽ "bám" vào <tbody> và cho phép kéo-thả các <tr>
    const sortable = Sortable.create(tbody, {
        animation: 150,     // Hiệu ứng kéo mượt
        disabled: true,     // Quan trọng: Bắt đầu ở trạng thái TẮT (không kéo được)
        // (Tùy chọn: Thêm CSS cho class 'sortable-ghost' để đẹp hơn)
    });

    // 3. Xử lý khi bấm nút "Sửa thứ tự"
    editBtn.addEventListener('click', () => {
        sortable.option('disabled', false); // BẬT kéo thả
        editBtn.style.display = 'none';     // Ẩn nút "Sửa"
        saveBtn.style.display = 'inline-block'; // Hiện nút "Lưu"

        // Thêm một class vào tbody để bạn có thể thêm CSS (ví dụ: đổi con trỏ chuột)
        tbody.classList.add('editing-order');
    });

    // 4. Xử lý khi bấm nút "Lưu thứ tự" (Submit form)
    taskForm.addEventListener('submit', (event) => {

        // A. Dừng form submit lại (khoan đã, tôi cần chuẩn bị)
        event.preventDefault();

        // B. Tìm tất cả các hàng <tr> trong tbody (theo thứ tự MỚI sau khi kéo)
        const rows = tbody.querySelectorAll('tr[data-task-id]');

        // C. Lặp qua từng hàng và tạo một <input> ẩn
        rows.forEach(row => {
            // Lấy ID từ 'data-task-id'
            const taskId = row.dataset.taskId;

            // Tạo thẻ <input type="hidden" name="taskOrder" value="[taskId]">
            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = 'taskOrder'; // Tên này phải khớp @RequestParam("taskOrder") trong Controller
            input.value = taskId;

            // Gắn input ẩn này vào <form>
            taskForm.appendChild(input);
        });

        // (Tắt kéo thả và đổi lại nút trước khi submit)
        sortable.option('disabled', true);
        editBtn.style.display = 'inline-block';
        saveBtn.style.display = 'none';
        tbody.classList.remove('editing-order');

        // D. Cho phép form submit (bây giờ form đã chứa đầy đủ ID theo thứ tự)
        taskForm.submit();
    });
});