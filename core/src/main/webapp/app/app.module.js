var app = angular.module('saveme',
    [
      'ngRoute',
      'ui.bootstrap',
      'ui.bootstrap.tpls',
      'ui.grid'
    ]);

app.run(['$rootScope', 'MessageService',
  function ($rootScope, messageService) {
    $rootScope.messageService = messageService;
  }
]);