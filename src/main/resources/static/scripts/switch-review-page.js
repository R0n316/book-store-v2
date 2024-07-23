const nextPageButton = document.getElementById('next');
const prevPageButton = document.getElementById('prev');

nextPageButton.addEventListener('click', () => {
    if(hasBooks()){
        let pageNumber = getPageNumber();
        editPageNumber(++pageNumber);
    }
});

prevPageButton.addEventListener('click', () => {
    let pageNumber = getPageNumber();
    if(pageNumber > 0){
        editPageNumber(--pageNumber);
    }
});

function getPageNumber(){
    const currentUrl = new URL(window.location.href);
    const page = 'page'
    return currentUrl.searchParams.get(page) || 0;
}

function editPageNumber(newValue){
    const currentUrl = new URL(window.location.href);
    const page = 'page';
    currentUrl.searchParams.set(page,newValue);
    window.location.href = currentUrl.toString();
}

function hasBooks(){
    const reviews = document.querySelectorAll('.review');
    return reviews.length === 18;
}