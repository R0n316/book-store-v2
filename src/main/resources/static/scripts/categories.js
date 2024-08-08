const url = window.location.pathname;
const tabs = document.querySelectorAll('.tab');
const urlParams = new URLSearchParams(window.location.search);
const category = urlParams.get('category');

if(url.endsWith('/books')) {
    tabs.forEach(tab => {
        tab.addEventListener('click', () => {
            tabs.forEach(t => t.classList.remove('active'));
            tab.classList.add('active');
        });

        if(category === null) {
            tabs[0].classList.add('active');
        } else if(tab.textContent === category) {
            tab.classList.add('active');
        }
    });
}

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
