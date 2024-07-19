const favoritesIcon = document.querySelectorAll('.favorites-icon');
const currentPath = window.location.pathname;
favoritesIcon.forEach(icon => icon.addEventListener('click',handleFavoritesCLick));

function handleFavoritesCLick(event){
    let bookId;
    let pattern = /^\/books\/(\d+)$/;
    if(!pattern.test(currentPath)){
        bookId = event.target.closest('.book-card').querySelector('a').href.split('/')[4];
    } else {
        const parts = currentPath.split('/');
        bookId = parts[parts.length - 1];
    }
    const currentSrc = event.target.src;
    let url;

    if(currentSrc.endsWith('favoritesIcon.svg')){
        url = `/api/books/${bookId}/toFavorites`;
    }
    else if(currentSrc.endsWith('markedFavoritesIcon.svg')){
        url = `/api/books/${bookId}/fromFavorites`;
    }

    fetch(url, {
        method: 'PATCH',
    })
        .then(response => {
            if (response.ok) {
                event.target.src = currentSrc.endsWith('favoritesIcon.svg')
                    ? '/static/images/markedFavoritesIcon.svg'
                    : '/static/images/favoritesIcon.svg';
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