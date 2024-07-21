const bookTitle = document.querySelector('.book-name').textContent.trim();
const bookAuthor = document.querySelector('.book-author').textContent.trim();


// Функция для отображения модального окна отзыва
function showReviewModal(event) {
    const url = window.location.pathname;
    const regex = /\/books\/(\d+)/;
    const matches = url.match(regex);
    const bookId = matches ? matches[1] : null;
    const userId = event.target.dataset.userId;
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
                                throw new Error('ошибка сервера');
                            }
                        })
                        .then(data => {
                            Swal.fire('Спасибо!', 'Ваш отзыв опубликован.', 'success');
                            const newReviewHtml =
                                `
                    <div class="swiper-slide">
      <div class="slide-container">
        <div class="review">
          <div class="content">
            <h2 class="book-author">${data.book.author}</h2>
            <h1 class="book-name">${data.book.name}</h1>
            <p class="text">${data.content}</p>
            <div class="full-review">
              <span class="text">Читать отзыв целиком</span>
              <div class="more-button">
                <img src="/static/images/more.svg" alt="">
              </div>
            </div>
            <div>
              <button class="delete-button" data-review-id="${data.id}">Удалить</button>
            </div>
          </div>
          <div class="info">
            <div class="avatar">
              <img src="/static/images/avatar.jpg" alt="">
            </div>
            <h3 class="username">${data.user.username}</h3>
            <h4 class="text">Понравился отзыв?</h4>
            <div class="likes">
              <div class="rating">
                <div>
                  <img class="like" src="/static/images/like.svg" alt="" data-id="${data.id}">
                </div>
                <span class="like-count" style="margin-top: 1px">0</span>
              </div>
              <div class="rating">
                <div>
                  <img class="dislike" src="/static/images/dislike.svg" alt="" data-id="${data.id}">
                </div>
                <span class="dislike-count">0</span>
              </div>
            </div>
            <div class="reply">
              <img src="/static/images/reply.svg" alt="">
              <span>Ответить</span>
            </div>
          </div>
        </div>
      </div>
    </div>
                                `;
                            const swiper = document.querySelector('.swiper').swiper;
                            swiper.prependSlide(newReviewHtml);
                            swiper.update();
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