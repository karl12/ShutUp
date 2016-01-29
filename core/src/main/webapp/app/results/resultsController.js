app.controller('ResultsCtrl', ['$scope', 'MeetingService',
  function ($scope, MeetingService) {
    $scope.labels = [];
    $scope.data = [];

    MeetingService.refresh().success(function (meeting) {
      var dataInner = [];
      angular.forEach(meeting.participants, function (participant) {
        if(participant.shutupScore > 0) {
          $scope.labels.push(participant.name);
          dataInner.push(participant.shutupScore);
        }
      });
      $scope.data.push(dataInner);
    });
}]);