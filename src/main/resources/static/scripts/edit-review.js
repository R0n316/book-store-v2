const reviewContent = document.querySelector('.text').textContent;


const editButtons = document.querySelectorAll('.edit-button');
editButtons.forEach(button => button.addEventListener('click',editReview));
function editReview(event) {
    const reviewId = event.target.dataset.reviewId;
    const userId = event.target.dataset.userId;
    const reviewTextElement = event.target.closest('.review').querySelector('.text');
    Swal.fire({
        title: bookTitle,
        html: `
        <p><strong>Автор:</strong> ${bookAuthor}</p>
        <textarea id="reviewText" class="swal2-textarea" placeholder="Введите ваш отзыв"
          style="resize: none; width:95%; margin-left:3%; margin-right:3%;">${reviewContent}</textarea>
      `,
        focusConfirm: false,
        showCancelButton: true,
        confirmButtonText: 'Обновить',
        cancelButtonText: 'Отмена',
        preConfirm: () => {
            const reviewText = Swal.getPopup().querySelector('#reviewText').value;
            if (!reviewText) {
                Swal.showValidationMessage('Пожалуйста, введите ваш отзыв.');
            }
            return { reviewText: reviewText };
        }
    }).then((result) => {
        if (result.isConfirmed) {
            fetch(`/api/reviews/${reviewId}`, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    bookId: bookId,
                    content: result.value.reviewText,
                    userId: userId
                })
            })
            .then(response => {
                if(response.status === 200){
                    console.log('response update successfully');
                    reviewTextElement.textContent = result.value.reviewText;
                } else{
                    throw new Error('ошибка сервера');
                }
            })
            .catch(error => console.error(`Error: ${error}`));
        }
    })
}