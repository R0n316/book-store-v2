const likeIcons = document.querySelectorAll('.like');
const dislikeIcons = document.querySelectorAll('.dislike');

likeIcons.forEach(icon => icon.addEventListener('click',reactToReview));
dislikeIcons.forEach(icon => icon.addEventListener('click',reactToReview));

function reactToReview(event){
    const reviewId = event.target.dataset.id;
    const reaction = event.target.className.toUpperCase();

    fetch(`/api/reviews/${reviewId}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'text/plain'
        },
        body: reaction
    })
        .then(response => {
            if(response.ok){
                console.log(`${reaction} sent successfully`)
            } else if(response.status === 403){
                console.log('Forbidden: Redirecting to login page');
                window.location.href = '/auth/login';
            } else {
                console.log('Like failed to send');
            }
        })
        .catch(error => console.error(`Error: ${error}`))
}