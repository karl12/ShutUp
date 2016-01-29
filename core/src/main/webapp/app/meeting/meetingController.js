app.controller('MeetingCtrl', ['$scope', '$interval', '$timeout', '$location', 'MeetingService',
  function ($scope, $interval, $timeout, $location, MeetingService) {
    $scope.value = 0;
    $scope.message = "Lets Begin!";
    $scope.type = 'success';
    $scope.showBarStatus = true;
    $scope.showBarText = "Hide bar";

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

        if($scope.value < 25 || (!$scope.showBarStatus && $scope.value < 75)) {
          $scope.type = 'success';
          $scope.message = "Yes, go on.";
        } else if($scope.value < 50) {
          $scope.type = 'info';
          $scope.message = "What are you talking about?";
        } else if($scope.value < 75) {
          $scope.type = 'warning';
          $scope.message = "Why are you still talking?";
        } else {
          $scope.type = 'danger';
          $scope.message = "SHUT UP!!";
        }
      }
    };

    $scope.resetBored = function() {
      MeetingService.resetBored();
    };

    $scope.setSpeaker = function(name) {
      MeetingService.setSpeaker(name);
    };

    $scope.showBar = function(){
      $scope.showBarStatus = !$scope.showBarStatus;
      $scope.showBarText = $scope.showBarStatus ? "Hide bar" : "Show bar";
    };

    $scope.endMeeting = function(){
      $location.path('/results');
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