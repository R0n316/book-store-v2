const bookCards = document.querySelectorAll('.book-cards');
bookCards.forEach(bookCard =>
    bookCard.addEventListener('wheel', event => handleWheel(event, bookCard)));