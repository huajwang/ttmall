$(function() {
  var anchor = window.location.hash;
  var params = anchor.slice(1).split('&');
  var token = {};

  $(params).each(function(idx, param) {
    var pair = param.split('=');
    var k = pair[0];
    var v = pair[1];
    token[k] = v;
  });

  $.ajax({
      url: 'http://localhost:8080/address',
      beforeSend: function(xhr) {
          xhr.setRequestHeader("Authorization", "Bearer " + token['access_token']);
      },
      success: function(data) {
        $("#address").text(data.streetNo + "\n" +  data.city + "\n" + data.province + "\n" + data.postalCode);
         window.location.replace("#");
      },
      error: function(xhr, status, error) {
          alert(status);
      }
  });
});
