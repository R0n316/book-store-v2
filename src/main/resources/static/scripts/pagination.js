document.addEventListener('DOMContentLoaded', function() {
    const leftArrow = document.querySelector('.left-arrow');
    const rightArrow = document.querySelector('.right-arrow');
    const currentPage = parseInt(document.querySelector('.active-number').textContent) - 1;
    const totalPages = parseInt(document.querySelectorAll('.pages-count li').length);

    function updateArrows() {
        if (currentPage === 0) {
            leftArrow.classList.add('unavailable');
        } else {
            leftArrow.classList.remove('unavailable');
        }

        if (currentPage === totalPages - 1) {
            rightArrow.classList.add('unavailable');
        } else {
            rightArrow.classList.remove('unavailable');
        }
    }

    function updateUrlParameter(key, value) {
        const url = new URL(window.location.href);
        url.searchParams.set(key, value);
        return url.toString();
    }

    leftArrow.addEventListener('click', function() {
        if (currentPage > 0) {
            window.location.href = updateUrlParameter('page', currentPage - 1);
        }
    });

    rightArrow.addEventListener('click', function() {
        if (currentPage < totalPages - 1) {
            window.location.href = updateUrlParameter('page', currentPage + 1);
        }
    });

    updateArrows();
});