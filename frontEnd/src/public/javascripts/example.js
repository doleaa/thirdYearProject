angular.module('ui.bootstrap.demo', ['ui.bootstrap']);
angular.module('ui.bootstrap.demo').controller('ModalDemoCtrl', ['$scope', '$http', '$uibModal', function ($scope, $http, $uibModal) {
  var $ctrl = this;

  $ctrl.animationsEnabled = true;
  $ctrl.currentNavItem = 'page1';
  $ctrl.sqlCommand = "";


  $ctrl.submit = function () {
    var reqBody = {
        "sqlCommand": $ctrl.sqlCommand,
        "dbMap": {
            "url": "jdbc:h2:/tmp/bam/test;AUTO_SERVER=TRUE",
            "username": "sa",
            "password": ""
        }
    };

    var config = {
        headers: {
            'Content-Type': 'application/json'
        }
    };
    $http.post('http://127.0.0.1:8090/command', reqBody, config).
    then(() => {
        console.log('dasfdsaf');
    },
    () => {
        console.log('fdsafsa');});

  };



//  $ctrl.open = function () {
//    $uibModal.open({
//      animation: $ctrl.animationsEnabled,
//      ariaLabelledBy: 'modal-title',
//      ariaDescribedBy: 'modal-body',
//      templateUrl: '../myModalContent.html',
//      controller: 'ModalInstanceCtrl',
//      controllerAs: '$ctrl'
//    });
//  };
}]);
//angular.module('ui.bootstrap.demo').controller('executeController', ['$scope', function ($scope) {
//    var $ctrl = this;
//
//}]);

// Please note that $uibModalInstance represents a modal window (instance) dependency.
// It is not the same as the $uibModal service used above.

angular.module('ui.bootstrap.demo').controller('ModalInstanceCtrl', function ($uibModalInstance) {
  var $ctrl = this;
  $ctrl.ok = function () {
    $uibModalInstance.close();
  };

  $ctrl.cancel = function () {
    $uibModalInstance.dismiss();
  };
});

// Please note that the close and dismiss bindings are from $uibModalInstance.

