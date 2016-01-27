app.controller('MessageCtrl', ['$scope',
  function ($scope) {
    $scope.messages = $scope.messageService.messages;
    $scope.closeMsg = function (index) {
      $scope.messages.splice(index, 1);
    };
  }]
);