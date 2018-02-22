package org.mobibank.payme.backend.operations;

/**
 * 
 * @author BEYE
 * 
 * une commande peut être exécutée tout de suite, mais elle doit pouvoir être annulée
 * à un instant ultérieur et indéfini
 * 
 * un objet Commande peut avoir une durée de vie indépendante de la requête originelle.
 * Il est donc nécessaire qu'un contexte ({@link Conversation}) maintienne une référence 
 * vers la commande exécutée.
 * 
 * Le nom Conversation souligne le fait qu'il arrive souvent que le résultat d'une suite
 * de commandes soit committé atomiquement, ce qui correspond à un scope conversation
 *
 * @param <C> 
 * 		type parameter C extends Command
 * 		Le but est d'avoir une seule interface Conversation commune aux variations,
 * 		tout en permettant l'utilisation par ses implémentations des déclinaisons de Command
 *
 */
@FunctionalInterface
public interface Command {
	
	/**
	 * Cette methode permet d'implementer le schema comptable propre à chaque opération.
	 */
	void execute();
}
