const { describe, expect, test } = require("@jest/globals");
const fs = require('fs');
const xmlParser = require('xml2json');

function readXmlFile(path) {
    const content = fs.readFileSync(path, 'utf8');
    return xmlParser.toJson(content, { object: true })
}

function readJsonFile(path) {
    const content = fs.readFileSync(path, 'utf8');
    return JSON.parse(content)
}

/**
 * @param {string} version the version to split
 */
function splitVersion(version) {
    return version.split('.').map(parseInt)
}

/**
 * @param {string} a first version
 * @param {string} b second version
 */
function versionComparator(a, b) {
    const [partsA, partsB] = [splitVersion(a), splitVersion(b)];
    if (partsA.length !== partsB.length) {
        throw Error(`Invalid version numbers: "${a}" and "${b}"`);
    }
    for (let i = 0; i < partsA.length; i++) {
        if (partsA[i] > partsB[i]) {
            return 1;
        } else if (partsA[i] < partsB[i]) {
            return -1;
        } else {
            continue;
        }
    }
}

function getLatestChangelogVersion() {
    const files = fs.readdirSync('../doc/changes/');
    const versions = files.map(f => f.match(/changes_([0-9.]+)\.md/))
        .filter(match => match !== null).map(match => match[1])
        .sort(versionComparator)
    return versions[versions.length - 1]
}

const changelogVersion = getLatestChangelogVersion();
describe(`Latest version number ${changelogVersion}`, () => {

    test("Parent pom", () => {
        const pom = readXmlFile("../pom.xml");
        const pomRevision = pom.project.properties.revision;
        expect(pomRevision).toBe(changelogVersion);
    });

    test("NPM package.json", () => {
        const packageJson = readJsonFile("package.json");
        const packageJsonVersion = packageJson.version;
        expect(packageJsonVersion).toBe(changelogVersion);
    });

    ["jdbc", "odbc"].forEach(type => {
        test(`Manifest of ${type} driver`, () => {
            const manifest = readXmlFile(`../src/exasol_${type}/manifest.xml`);
            const manifestVersion = manifest["connector-plugin"]["plugin-version"];
            expect(manifestVersion).toBe(changelogVersion);
        });
    });
});