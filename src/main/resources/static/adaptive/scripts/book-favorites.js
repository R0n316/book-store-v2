const favoritesIcon = document.querySelectorAll('.favorites');
const currentPath = window.location.pathname;
favoritesIcon.forEach(icon => icon.addEventListener('click',handleFavoritesCLick));

function handleFavoritesCLick(event){
    let bookId = Number(event.target.dataset.userId);
    let pattern = /^\/books\/(\d+)$/;
    // if(!pattern.test(currentPath)){
    //     bookId = event.target.closest('.book-card').querySelector('a').href.split('/')[4];
    // } else {
    //     const parts = currentPath.split('/');
    //     bookId = parts[parts.length - 1];
    // }
    const currentSrc = event.target.src;
    let url;

    if(currentSrc.endsWith('favorites.svg')){
        url = `/api/books/${bookId}/toFavorites`;
    }
    else if(currentSrc.endsWith('marked-favorites.svg')){
        url = `/api/books/${bookId}/fromFavorites`;
    }

    fetch(url, {
        method: 'PATCH',
    })
        .then(response => {
            if (response.ok) {
                event.target.src = currentSrc.endsWith('favoritesIcon.svg')
                    ? '/static/adaptive/images/marked-favorites.svg'
                    : '/static/adaptive/images/favorites.svg';
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