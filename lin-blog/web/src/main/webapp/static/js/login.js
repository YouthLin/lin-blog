$(document).ready(function () {
    // region login, setup
    $('#login-form').submit(function () {
        var user = $('#user');
        var plain = $('#plain');
        var pass = $('#pass');
        pass.val($.md5(user.val() + plain.val()));
    });
    // endregion

    //region dashboard
    var sidebar = $("#sidebar");
    var main = $("#main");
    sidebar.on('show.bs.collapse', function () {
        if (!sidebar.hasClass('in')) {
            main.addClass("col-xs-offset-4");
            main.addClass("col-xs-8");
        }
    });
    sidebar.on('hidden.bs.collapse', function () {
        if (!sidebar.hasClass('in')) {
            main.removeClass("col-xs-offset-4");
            main.removeClass("col-xs-8");
        }
    });

    $('body.nav-fixed').click(function () {
        $('#user-menu').collapse('hide');
    });
    //endregion

});
