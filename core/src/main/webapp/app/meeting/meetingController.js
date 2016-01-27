app.controller('MeetingCtrl', ['$scope', '$uibModal', '$interval', '$timeout', 'MeetingService',
  function ($scope, $uibModal, $interval, $timeout, MeetingService) {

    $scope.createMeeting = function(meeting) {
      MeetingService.createMeeting(meeting.hostName).success(function(meeting){
        $scope.meeting = meeting;
      });
    };

    $scope.totalBored = function(){
      if($scope.meeting && $scope.meeting.participants.length) {
        var numberBored = 0;
        angular.forEach($scope.meeting.participants, function(participant){
          if(participant.bored) numberBored++;
        });
        return numberBored + " bored out of " + $scope.meeting.participants.length;
      }
    };

    $interval(function () {
      if($scope.meeting) {
        MeetingService.refresh().success(function (meeting) {
          $scope.meeting = meeting;
        });
      }
    }, 1000);
  }]
);