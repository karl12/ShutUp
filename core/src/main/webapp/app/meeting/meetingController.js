app.controller('MeetingCtrl', ['$scope', '$uibModal', 'MeetingService',
  function ($scope, $uibModal, MeetingService) {

    $scope.createMeeting = function(meeting) {
      MeetingService.createMeeting(meeting.hostName, meeting.meetingName);
    };
  }]
);