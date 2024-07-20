const deleteButtons = document.querySelectorAll('.delete-button');
deleteButtons.forEach(button =>
    button.addEventListener('click',deleteReview));

function deleteReview(event){
    event.preventDefault();
    const reviewId = event.target.dataset.reviewId;

    fetch(`/api/reviews/${reviewId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'text/plain'
        },
        body: reviewId
    })
        .then(response => {
            if(response.ok){
                console.log(`review ${reviewId} delete successfully`);
                const slide = event.target.closest('.swiper-slide');
                const swiper = slide.closest('.swiper').swiper; // Получить экземпляр Swiper JS перед удалением слайда
                slide.remove();
                swiper.update(); // Обновить Swiper JS после удаления слайда
            }
        })
        .catch(error => console.error(`error: ${error}`));
}