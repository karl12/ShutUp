app.factory('MessageService', [
  function () {
    var messageServiceFunctions = {
      messages: [],
      addErrorMessage: function (messageText) {
        var message = {text: messageText, severity: 'danger'};
        this.messages.push(message);
      },
      addSuccessMessage: function (messageText) {
        var message = {text: messageText, severity: 'success'};
        this.messages.push(message);
      },
      serverErrorHandler: function (response) {
        console.log(response.errorMessage);
        messageServiceFunctions.addErrorMessage(response.errorMessage);
      }
    };
    return messageServiceFunctions;
  }
]);