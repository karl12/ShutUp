'use strict';

var app = angular.module('shutup',
    [
      'ngRoute',
      'ngAnimate',
      'ui.bootstrap',
      'ui.bootstrap.tpls'
    ]);

app.run(['$rootScope', 'MessageService',
  function ($rootScope, messageService) {
    $rootScope.messageService = messageService;
  }
]);