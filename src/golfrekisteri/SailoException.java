package golfrekisteri;

/**
 * Poikkeusluokka poikkeuksille
 * @author Niko Kynsijarvi
 * @version 23.2.2021
 *
 */
public class SailoException extends Exception {
    private static final long serialVersionUID = 1L;
    
    /**Tuodaan poikkeuksessa kaytettava viesti
     * @param viesti tuotu viesti
     */
    public SailoException(String viesti) {
                 super(viesti);
             }

}
