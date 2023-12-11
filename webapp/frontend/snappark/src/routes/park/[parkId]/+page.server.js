/** @type {import('./$types').PageLoad} */
export async function load({ params }) {
    // TODO: Refactor to validate requester as a manager of this park and deny if not a manager

    // Fetch park info - VULNERABLE !!!

    // TODO: Refactor so only one API request is done instead of several
    const parkGeneral = await fetch('http://app:9090/api/v1/parks/' + params.parkId);
    const avgLight = await fetch('http://app:9090/api/v1/parks/' + params.parkId + '/avgLight');
    const sensorCount = await fetch('http://app:9090/api/v1/parks/' + params.parkId + '/sensorCount');
    //const occupancies = await fetch('http://app:9090/api/v1/parks/' + params.parkId + '/occupancies');
    const occupancies = [{"date":1,"lotation":3},{"date":2,"lotation":2}];

    const avgLightBody = avgLight.text() ?? 0;
    const sensorCountBody = sensorCount.text() ?? 0;

	return {
        park: parkGeneral.json(),
        avgLight: avgLightBody,
        sensorCount: sensorCountBody,
        occupancies: occupancies
	};
}

