import java.util.List;

public interface ArcEndpointInterface {
	List<ArcInterface> getArcInputs();
	List<ArcInterface> getArcOutputs();

	void addArcInput(ArcInterface a);
	void addArcOutput(ArcInterface a);

	void removeArcInput(ArcInterface a);
	void removeArcOutput(ArcInterface a);
}
