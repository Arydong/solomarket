$(document).ready(function(){
    $("#category-toggle").click(function(){
        $(".dropdown-menu").toggle();
    });
    $(document).click(function(event) {
        if (!$(event.target).closest(".dropdown").length) {
            $(".dropdown-menu").hide();
        }
    });
});