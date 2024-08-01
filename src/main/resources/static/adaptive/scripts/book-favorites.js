const favoritesIcon = document.querySelectorAll('.favorites');
const currentPath = window.location.pathname;
favoritesIcon.forEach(icon => icon.addEventListener('click',handleFavoritesCLick));

function handleFavoritesCLick(event){
    let bookId = Number(event.target.dataset.userId);
    const currentSrc = event.target.src;
    let url;

    if(currentSrc.endsWith('marked-favorites.svg')){
        url = `/api/books/${bookId}/fromFavorites`;
    }
    else if(currentSrc.endsWith('favorites.svg')){
        url = `/api/books/${bookId}/toFavorites`;
    }

    fetch(url, {
        method: 'PATCH',
    })
        .then(response => {
            if (response.ok) {
                event.target.src = currentSrc.endsWith('marked-favorites.svg')
                    ? '/static/adaptive/images/favorites.svg'
                    : '/static/adaptive/images/marked-favorites.svg';
            } else if (response.status === 403) {
                window.location.href = '/auth/login';
            } else {
                console.error('Failed to update book favorites status');
            }
        })
        .catch(error => {
            console.error('Error:',error);
        });
}