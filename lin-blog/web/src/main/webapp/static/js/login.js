$(document).ready(function () {
    $('#login-form').submit(function () {
        var user = $('#user');
        var plain = $('#plain');
        var pass = $('#pass');
        pass.val($.md5(user.val() + plain.val()));
    });
});