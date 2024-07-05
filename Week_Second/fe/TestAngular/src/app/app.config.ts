import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideClientHydration } from '@angular/platform-browser';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { provideToastr } from 'ngx-toastr';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideIcons } from '@ng-icons/core';
import * as bootstrapIcon from '@ng-icons/bootstrap-icons';
import { config } from 'process';
import { JwtHelperService, JwtModule } from "@auth0/angular-jwt";

export function tokenGetter(){
  return localStorage.getItem('token');
}

export const appConfig: ApplicationConfig = {
  providers: [provideZoneChangeDetection({ eventCoalescing: true }), provideRouter(routes), provideClientHydration(), provideHttpClient(withFetch()), provideToastr(), provideAnimations(), provideIcons(bootstrapIcon),]
};
