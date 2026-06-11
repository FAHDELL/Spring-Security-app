import { Injectable } from '@angular/core';
import { KeycloakProfile } from 'keycloak-js';
import { KeycloakEventType, KeycloakService } from 'keycloak-angular';

//@Injectable({providedIn : "root"})
export class SecurityService {
  public profile?: KeycloakProfile;
  constructor(public kcService: KeycloakService) {
    this.init();
  }
  init() {
    this.kcService.keycloakEvents$.subscribe({
      next: (e) => {
        console.log('Event reçu:', e);

        // Correction : forcer le type vers string
        if ((e.type as unknown as string) === 'AuthSuccess') {
          console.log('Connexion réussie !');
          this.kcService.loadUserProfile().then((profile) => {
            this.profile = profile;
          });
        }
      },
      error: (err) => {
        console.error('Erreur Keycloak:', err);
      },
    });
  }

  public hasRoleIn(roles: string[]): boolean {
    let userRoles = this.kcService.getUserRoles();
    for (let role of roles) {
      if (userRoles.includes(role)) return true;
    }
    return false;
  }
}
