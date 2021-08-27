/*  ---------------------------------------------------
    Theme Name: Azenta
    Description:
    Author:
    Author URI:
    Version: 1.0
    Created:
---------------------------------------------------------  */

(function ($) {

    /*------------------
        Carousel Slider
    --------------------*/
    var hero_s = $(".hero-items");
    var thumbnailSlider = $(".thumbs");
    var duration = 500;
    var syncedSecondary = true;

    setTimeout(function () {
        $(".cloned .item-slider-model a").attr("data-fancybox", "group-2");
    }, 500);

    // carousel function for thumbnail slider
    thumbnailSlider.on("initialized.owl.carousel", function () {
        thumbnailSlider
            .find(".owl-item")
            .eq(0)
            .addClass("current");
    }).owlCarousel({
        loop: false,
        items: 3,
        nav: false,
        margin: 0,
        smartSpeed: 1200,
        responsive: {
            320: {
                items: 2,
                margin: 3
            },
            480: {
                items: 3,
                margin: 3
            },
            768: {
                items: 3,
                margin: 3
            },
            1200: {
                items: 3,
                margin: 3
            }
        }
    })
        .on("changed.owl.carousel", syncPosition2);

    function syncPosition(el) {
        var count = el.item.count - 1;
        var current = Math.round(el.item.index - el.item.count / 2 - 0.5);

        if (current < 0) {
            current = count;
        }
        if (current > count) {
            current = 0;
        }

        thumbnailSlider
            .find(".owl-item")
            .removeClass("current")
            .eq(current)
            .addClass("current");
        var onscreen = thumbnailSlider.find(".owl-item.active").length - 1;
        var start = thumbnailSlider
            .find(".owl-item.active")
            .first()
            .index();
        var end = thumbnailSlider
            .find(".owl-item.active")
            .last()
            .index();

        if (current > end) {
            thumbnailSlider.data("owl.carousel").to(current, 100, true);
        }
        if (current < start) {
            thumbnailSlider.data("owl.carousel").to(current - onscreen, 100, true);
        }
    }

    function syncPosition2(el) {
        if (syncedSecondary) {
            var number = el.item.index;
            // slider.data("owl.carousel").to(number, 100, true);
            thumbnailSlider.data("owl.carousel").to(number, 100, true);
        }
    }



})(jQuery);