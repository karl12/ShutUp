'use strict';

var app = angular.module('shutup',
    [
      'ngRoute',
      'ui.bootstrap',
      'ui.bootstrap.tpls'
    ]);

app.run(['$rootScope', 'MessageService',
  function ($rootScope, messageService) {
    $rootScope.messageService = messageService;
  }
]);