const url = window.location.pathname;
const regex = /\/books\/(\d+)/;
const matches = url.match(regex);
const bookId = matches ? matches[1] : null;

document.querySelector('.reviews').addEventListener('click', event => {
    if (event.target.closest('.edit-button')) {
        const reviewTextElement = event.target.closest('.review').querySelector('.text');
        const reviewContent = reviewTextElement.textContent;
        editReview(event,reviewContent);
    }
});

function editReview(event,reviewContent) {
    const reviewElement = event.target.closest('.review');
    const bookTitle = reviewElement.querySelector('.book-name').textContent.trim();
    const bookAuthor = reviewElement.querySelector('.book-author').textContent.trim();
    const reviewId = event.target.dataset.reviewId;
    const userId = event.target.dataset.userId;
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
                    event.target.closest('.review').querySelector('.text').textContent = result.value.reviewText;
                } else{
                    throw new Error('ошибка сервера');
                }
            })
            .catch(error => console.error(`Error: ${error}`));
        }
    })
}