app.controller('MeetingCtrl', ['$scope', '$uibModal', '$interval', '$timeout', 'MeetingService',
  function ($scope, $uibModal, $interval, $timeout, MeetingService) {
    $scope.value = 0;
    $scope.message = "Lets Begin!";
    $scope.type = 'success';

    $scope.createMeeting = function(meeting) {
      MeetingService.createMeeting(meeting.hostName).success(function(meeting){
        $scope.meeting = meeting;
      });
    };

    var updateProgressBar = function(){
      if($scope.meeting && $scope.meeting.participants.length) {
        var numberBored = 0;
        angular.forEach($scope.meeting.participants, function(participant){
          if(participant.bored) numberBored++;
        });

        $scope.value = Math.floor((numberBored / $scope.meeting.participants.length) * 100);

        if ($scope.value < 25) {
          $scope.type = 'success';
          $scope.message = "Yes, go on.";
        } else if ($scope.value < 50) {
          $scope.type = 'info';
          $scope.message = "What are you talking about?";
        } else if ($scope.value < 75) {
          $scope.type = 'warning';
          $scope.message = "Why are you still talking about this?";
        } else {
          $scope.type = 'danger';
          $scope.message = "SHUT UP!!";
        }

      }
    };

    $scope.resetBored = function() {
      MeetingService.resetBored();
    };

    $interval(function () {
      if($scope.meeting) {
        MeetingService.refresh().success(function (meeting) {
          $scope.meeting = meeting;
          updateProgressBar();
        });
      }
    }, 1000);
  }]
);