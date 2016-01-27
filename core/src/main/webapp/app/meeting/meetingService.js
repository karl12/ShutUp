app.factory('MeetingService', ['$http',
  function ($http) {
    return {

      createMeeting: function(hostName, meetingName) {
        var url = "/api/create-meeting";
        return $http.post(url, {
          host: hostName,
          name: meetingName
        });
      }
    }
  }
]);
