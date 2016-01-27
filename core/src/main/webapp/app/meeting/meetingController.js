app.controller('DashboardCtrl', ['$scope', 'AccountService', '$uibModal',
  function ($scope, AccountService, $uibModal) {

    AccountService.getAllAccountDetails("test").success(function(accounts){
      $scope.accounts = accounts;
    });

    $scope.selectAccount = function(selectedAccount){
      AccountService.getAccount(selectedAccount.name).success(function(account){
        $scope.selectedAccount = account;
        $scope.gridOptions.data = account.transactions;
      });
    };

    $scope.openAccountCreationModal = function () {
      $uibModal.open({
        animation: true,
        templateUrl: '/app/templates/account-creation-modal.html',
        controller: 'AccountCreationCtrl'
      });
    };

    $scope.gridOptions = {
      onRegisterApi: function(gridApi) {
        $scope.gridApi = gridApi;
      }
    };
  }]
);