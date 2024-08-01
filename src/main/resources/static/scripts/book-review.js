document.querySelector('.reviews').addEventListener('click', event => {
    if (event.target.closest('.like') || event.target.closest('.dislike')) {
        reactToReview(event);
    }
});

function reactToReview(event){
    const reviewId = event.target.dataset.id;
    const reaction = event.target.className.toUpperCase();
    let reviewContainer = event.target.closest('.review');
    const likeIcon = reviewContainer.querySelector('.like');
    const dislikeIcon = reviewContainer.querySelector('.dislike');
    const likeCount = reviewContainer.querySelector('.like-count');
    const dislikeCount = reviewContainer.querySelector('.dislike-count')
    let likeFileName = likeIcon.src.substring(likeIcon.src.lastIndexOf('/') + 1);
    let dislikeFileName = dislikeIcon.src.substring(dislikeIcon.src.lastIndexOf('/') + 1);

    fetch(`/api/reviews/${reviewId}/respond`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'text/plain'
        },
        body: reaction
    })
        .then(response => {
            if(response.ok){
                console.log(`${reaction} sent successfully`);

                if(reaction === 'LIKE'){
                    if(likeFileName === 'marked-like.svg'){
                        likeFileName = 'like.svg';
                        likeCount.textContent = parseInt(likeCount.textContent) - 1;
                    }
                    else {
                        if(dislikeFileName === 'marked-dislike.svg'){
                            dislikeFileName = 'dislike.svg'
                            dislikeCount.textContent = parseInt(dislikeCount.textContent) - 1;
                        }
                        likeFileName = 'marked-like.svg';
                        likeCount.textContent = parseInt(likeCount.textContent) + 1;
                    }
                }
                else if (reaction === 'DISLIKE') {
                    if (dislikeFileName === 'marked-dislike.svg') {
                        dislikeFileName = 'dislike.svg';
                        dislikeCount.textContent = parseInt(dislikeCount.textContent) - 1;
                    }
                    else {
                        if (likeFileName === 'marked-like.svg') {
                            likeFileName = 'like.svg';
                            likeCount.textContent = parseInt(likeCount.textContent) - 1;
                        }
                        dislikeFileName = 'marked-dislike.svg';
                        dislikeCount.textContent = parseInt(dislikeCount.textContent) + 1;
                    }
                }

                likeIcon.src = likeIcon.src.substring(0,likeIcon.src.lastIndexOf('/') + 1) + likeFileName;
                dislikeIcon.src = dislikeIcon.src.substring(0,dislikeIcon.src.lastIndexOf('/') + 1) + dislikeFileName;
            } else if(response.status === 403){
                console.log('Forbidden: Redirecting to login page');
                window.location.href = '/auth/login';
            } else {
                console.log('Like failed to send');
            }
        })
        .catch(error => console.error(`Error: ${error}`))
}