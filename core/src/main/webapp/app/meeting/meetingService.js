app.factory('AccountService', ['$http',
  function ($http) {
    return {

      getAllAccountDetails: function() {
        var url = "/api/all-accounts";
        return $http.get(url);
      },

      getAccount: function(accountName) {
        var url = "/api/account?name=" + accountName;
        return $http.get(url);
      },

      addAccount: function(account) {
        var url = "/api/add-account?name=" + account.name
            + "&bank=" + account.bank
            + "&lowerLimit=" + account.lowerLimit
            + "&type=" + account.accountType
            + "&currency=" + account.currency;
        return $http.post(url);
      }
    }
  }
]);
