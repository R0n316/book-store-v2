$(document).ready(function () {
    let lastClickTime = 0;

    $('.block-title').click(function (event) {
        const currentTime = new Date().getTime();
        if (currentTime - lastClickTime >= 200) {
            if ($('.block').hasClass('one')) {
                $('.block-title').not($(this)).removeClass('active');
                $('.block-content').not($(this).next()).slideUp(300);
            }
            $(this).toggleClass('active').next().slideToggle(300);
            lastClickTime = currentTime;
        }
    });
});