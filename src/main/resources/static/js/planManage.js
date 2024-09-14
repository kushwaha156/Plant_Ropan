var app = angular.module('planManage', ['ui.bootstrap']);

app.controller('planManageController', ['$scope', '$http', function($scope, $http) {
    console.log("AngularJS controller initialized");

    // Function to handle form submission
    $scope.submitPlan = function() {
        console.log("Submitting form...");

        // Create a FormData object to send data as multipart
        var formData = new FormData();
        formData.append('plansName', $scope.plan.plansName);
        formData.append('plansRs', $scope.plan.plansRs);
        formData.append('timeDuration', $scope.plan.timeDuration);
        formData.append('UptoPots', $scope.plan.UptoPots);
        formData.append('packs', $scope.plan.packs);
        formData.append('VisitsMonths', $scope.plan.VisitsMonths);
  
        $http({
            method: 'POST',
            url: 'addPlans',
            data: formData,
            headers: { 'Content-Type': undefined  }, 
            transformRequest: angular.identity 
        }).then(function successCallback(response) {
            if (response.data.message === 'Plans Add Successfully') {
                Swal.fire({
                    title: response.data.message,
                    text: 'Thanks!!',
                    icon: 'success',
                    confirmButtonText: 'OK',
                });
            } else {
                Swal.fire({
                    title: response.data.message,
                    text: 'Thanks!!',
                    icon: 'warning',
                    confirmButtonText: 'OK',
                });
            }
        }, function errorCallback(response) {
            Swal.fire({
                title: 'Error',
                text: 'Something went wrong!',
                icon: 'error',
                confirmButtonText: 'OK',
            });
        });
      
    };
}]);
