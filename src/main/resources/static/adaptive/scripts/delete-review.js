document.querySelector('.reviews').addEventListener('click', event => {
    if (event.target.closest('.delete-button')) {
        deleteReview(event);
    }
});

function deleteReview(event) {
    event.preventDefault();
    const reviewId = event.target.dataset.reviewId;

    // Добавляем модальное окно подтверждения перед удалением
    Swal.fire({
        title: 'Вы уверены?',
        text: 'Вы точно хотите удалить этот отзыв?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Да, удалить!',
        cancelButtonText: 'Отмена'
    }).then((result) => {
        if (result.isConfirmed) {
            fetch(`/api/reviews/${reviewId}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'text/plain'
                },
                body: reviewId
            })
        .then(response => {
                if (response.ok) {
                    console.log(`Review ${reviewId} deleted successfully`);
                    const slide = event.target.closest('.swiper-slide');
                    const swiperEl = slide.closest('.swiper');
                    if(swiperEl != null){
                        const swiper = swiperEl.swiper;
                        slide.remove();
                        swiper.update();
                    } else{
                        slide.remove();
                    }

                    Swal.fire(
                        'Удалено!',
                        'Ваш отзыв был успешно удален.',
                        'success'
                    );
                }
            })
                .catch(error => console.error(`Error: ${error}`));
        }
    });
}