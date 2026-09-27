import { inject } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import { Router } from '@angular/router';
import { map } from 'rxjs';
import { AuthService } from '../services/auth.service';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (!authService.isLoggedIn()) {
    return router.createUrlTree(['/login']);
  }

  return authService.getCurrentUser().pipe(
    map(user => {
      const requiredRole = route.data?.['role'];

      if (requiredRole && user.role !== requiredRole) {
        if (user.role === 'PATIENT') {
          return router.createUrlTree(['/patient/dashboard']);
        }

        if (user.role === 'DOCTOR') {
          return router.createUrlTree(['/doctor/dashboard']);
        }
      }

      return true;
    })
  );
};