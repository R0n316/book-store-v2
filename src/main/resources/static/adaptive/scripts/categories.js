const tabs = document.querySelectorAll('.tab');

tabs.forEach(tab => {
    tab.addEventListener('click', () => {
        tabs.forEach(t => t.classList.remove('active'));
        tab.classList.add('active');
    })
});

const categories = document.querySelector('.categories');


let scrollTimeout;
let isScrolling = false;

function scrollOverflowedDiv(event, element) {
    event.preventDefault();
    element.scrollLeft += event.deltaY;
}

function handleWheel(event, element) {
    if (!isScrolling) {
        isScrolling = true;
        scrollTimeout = setTimeout(() => {
            isScrolling = false;
        }, 500);
    }
    if (isScrolling) {
        scrollOverflowedDiv(event, element);
    }
}

categories.addEventListener('wheel', event => handleWheel(event, categories));
