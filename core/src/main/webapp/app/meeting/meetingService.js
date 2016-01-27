app.factory('MeetingService', ['$http',
  function ($http) {
    return {

      createMeeting: function(hostName) {
        return $http({
          url: '/api/create-meeting',
          method: "POST",
          headers: {'Content-Type': 'application/x-www-form-urlencoded'},
          data: $.param({'host' : hostName})
        });
      }
    }
  }
]);
