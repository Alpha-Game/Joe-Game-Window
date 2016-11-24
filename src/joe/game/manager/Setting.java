package joe.game.manager;

public class Setting {
	private final String fIdentifier;
	private final String fLanguageIdentifier;
	private final Class<?> fType;
	
	public Setting(String identifier, String languageIdentifier, Class<?> type) {
		if (identifier == null) {
			throw new IllegalArgumentException("identifier");
		} else if (languageIdentifier == null) {
			throw new IllegalArgumentException("languageIdentifier");
		} else if (type == null) {
			throw new IllegalArgumentException("type");
		}
		
		fIdentifier = identifier;
		fLanguageIdentifier = languageIdentifier;
		fType = type;
	}
	
	public String getIdentifier() {
		return fIdentifier;
	}
	
	public String getLanguageIdentifier() {
		return fLanguageIdentifier;
	}
	
	public Class<?> getType() {			
		return fType;
	}
	
	public String toString() {
		return fIdentifier;
	}
	
	@Override
	public int hashCode() {
		return fIdentifier.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Setting) {
			return fIdentifier.equals(((Setting) obj).fIdentifier);
		} else if (obj instanceof String) {
			fIdentifier.equals(obj);
		}
		return super.equals(obj);
	}
}
