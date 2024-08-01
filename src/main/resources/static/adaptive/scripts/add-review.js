

// Функция для отображения модального окна отзыва
function showReviewModal(event) {
    const userId = event.target.dataset.userId;
    const bookTitle = event.target.dataset.bookTitle;
    const bookAuthor = event.target.dataset.bookAuthor;
    if (!userId) {
        // Пользователь не авторизован
        Swal.fire({
            title: 'Только для авторизованных пользователей',
            html: 'Вы должны быть авторизованы, чтобы оставлять отзывы.',
            showCancelButton: true,
            confirmButtonText: 'Войти',
            cancelButtonText: 'Отмена',
        }).then((result) => {
            if (result.isConfirmed) {
                window.location.href = '/auth/login';
            }
        });
    } else{
        Swal.fire({
            title: bookTitle,
            html: `
      <p><strong>Автор:</strong> ${bookAuthor}</p>
      <textarea id="reviewText" class="swal2-textarea" placeholder="Введите ваш отзыв" 
      style="resize: none; width:95%; margin-left:3%; margin-right:3%;"></textarea>
    `,
            focusConfirm: false,
            showCancelButton: true,
            confirmButtonText: 'Опубликовать',
            cancelButtonText: 'Отмена',
            preConfirm: () => {
                const reviewText = Swal.getPopup().querySelector('#reviewText').value;
                if (!reviewText) {
                    Swal.showValidationMessage('Пожалуйста, введите ваш отзыв.');
                }
                return { reviewText: reviewText };
            }
        })
            .then((result) => {
                if (result.isConfirmed) {
                    fetch('/api/reviews', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({
                            bookId: bookId,
                            content: result.value.reviewText
                        })
                    })
                        .then(response => {
                            if(response.status === 201){
                                console.log('review create successfully');
                                return response.json();
                            } else{
                                throw new Error('server error');
                            }
                        })
                        .then(data => {
                            Swal.fire('Спасибо!', 'Ваш отзыв опубликован.', 'success');
                            const newReviewHtml =
                                `
        <div class="review swiper-slide">
            <h2 class="review-book-author">${data.book.author}</h2>
            <h1 class="review-book-name">${data.book.name}</h1>
            <p class="content">${data.content}</p>
            <div class="full-review">
              <span>Читать отзыв целиком</span>
              <div class="more-icon">
                <img src="/static/images/more.svg" alt="more">
              </div>
            </div>
            <div class="user-info">
              <div class="avatar">
                <img src="/static/adaptive/images/avatar.jpg" alt="avatar">
              </div>
              <span>${data.user.username}</span>
            </div>
            <div class="review-reactions">
              <div class="reaction">
                <img class="like" src="/static/images/like.svg" alt="like" data-id="${data.id}">
                <span class="like-count" style="margin-top: 1px">0</span>
              </div>
              <div class="reaction">
                <img class="dislike" src="/static/images/dislike.svg" alt="dislike" data-id="${data.id}">
                <span class="like-count" style="margin-top: 1px">0</span>
              </div>
            </div>
            <div class="review-buttons">
              <button class="delete-button" data-review-id="${data.id}">Удалить</button>
              <button class="edit-button" data-review-id="${data.id}" data-user-id="${data.user.id}">Изменить</button>
            </div>
          </div>
                                `;
                            const swiper = document.querySelector('.swiper').swiper;
                            swiper.prependSlide(newReviewHtml);
                            swiper.update();
                            swiper.slideTo(0);
                        })
                        .catch(error => {
                            console.error('Произошла ошибка при отправке отзыва:', error);
                            Swal.fire('Ошибка!', `Произошла ошибка при отправке отзыва.`, 'error');
                        })
                }
            })
    }
}

const openReviewModalButton = document.querySelector('.add-review');
openReviewModalButton.addEventListener("click", showReviewModal);