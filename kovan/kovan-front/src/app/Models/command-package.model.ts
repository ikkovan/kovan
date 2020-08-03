import { Rule } from './rule.model';
import {Statement} from './statement.model'
import {Parameter} from './parameter.model'
export class CommandPackage {
    command :Rule;
    variables : Parameter[];
    statements : Statement[];
}
