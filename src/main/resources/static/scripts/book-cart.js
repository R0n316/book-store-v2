const toCartButton = document.querySelectorAll('.add-to-cart-button');

toCartButton.forEach(button => {
    button.addEventListener('click',handleToCartButtonCLick);
})

function handleToCartButtonCLick(event){
    const bookId = event.target.closest('.book-card').querySelector('a').href.split('/')[4];
    const currentText = event.target.textContent;
    let url;
    if(currentText === 'В корзину'){
        url = `/api/books/${bookId}/toCart`;
    }
    else if(currentText === 'Удалить'){
        url = `/api/books/${bookId}/fromCart`;
    }

    fetch(url,{
        method: 'PATCH',
    })
        .then(response => {
            if(response.ok){
                event.target.innerText = currentText === 'В корзину'
                    ? 'Удалить'
                    : 'В корзину'
            } else if(response.status === 403){
                window.location.href = '/auth/login';
            } else{
                console.error('Failed to add book to cart');
            }
        })
        .catch(error => {
            console.error('Error:',error)
        })
}