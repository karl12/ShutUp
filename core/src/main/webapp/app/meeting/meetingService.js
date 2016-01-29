app.factory('MeetingService', ['$http',
  function ($http) {
    return {
      resetBored: function(){
        return $http({
          url: '/api/reset-bored',
          method: "POST"
        })
      },

      refresh: function() {
        return $http.get('api/host-connect');
      },

      setSpeaker: function(name) {
        return $http({
          url: '/api/set-speaker',
          method: "POST",
          headers: {'Content-Type': 'application/x-www-form-urlencoded'},
          data: $.param({'name' : name})
        });
      },

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
