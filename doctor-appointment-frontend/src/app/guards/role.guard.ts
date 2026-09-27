import { inject } from '@angular/core';

import {
  CanActivateFn,
  ActivatedRouteSnapshot,
  Router
} from '@angular/router';

import { map } from 'rxjs';

import { AuthService } from '../services/auth.service';

export const roleGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot
) => {

  const authService = inject(AuthService);
  const router = inject(Router);

  // User is not logged in
  if (!authService.isLoggedIn()) {
    return router.createUrlTree(['/login']);
  }

  const expectedRole =
    route.data['role'] as string;

  const token = authService.getToken();

  if (!token) {
    return router.createUrlTree(['/login']);
  }

  try {

    // Check that JWT has a valid payload
    JSON.parse(
      atob(token.split('.')[1])
    );

    return authService
      .getCurrentUser()
      .pipe(
        map(user => {

          // Correct role
          if (user.role === expectedRole) {
            return true;
          }

          // Redirect based on actual role
          if (user.role === 'ADMIN') {
            return router.createUrlTree(
              ['/admin/dashboard']
            );
          }

          if (user.role === 'DOCTOR') {
            return router.createUrlTree(
              ['/doctor/dashboard']
            );
          }

          return router.createUrlTree(
            ['/patient/dashboard']
          );
        })
      );

  } catch (error) {

    console.error(
      'Invalid JWT:',
      error
    );

    authService.logout();

    return router.createUrlTree(['/login']);
  }
};