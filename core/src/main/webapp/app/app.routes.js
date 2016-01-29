app.config(['$routeProvider',
  function ($routeProvider) {

    $routeProvider.
    when('/meeting', {
      templateUrl: 'app/meeting/meeting.html',
      controller: 'MeetingCtrl'
    }).
    when('/results', {
      templateUrl: 'app/results/results.html',
      controller: 'ResultsCtrl'
    }).
    otherwise({
      redirectTo: '/meeting'
    });
  }]);